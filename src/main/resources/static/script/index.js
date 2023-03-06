console.log("Index: JS OK!");

photoList();

function photoList() {
	const photoListContainer = document.querySelector('#photo-list');
	const categorySelect = document.querySelector('#category-select');

	axios.get('http://localhost:8080/api/photos')
		.then((res) => {
			const photosData = res.data.photos;
			const categories = res.data.categories;
			categories.forEach(category => {
				const option = `<option value="${category.name}">${category.name}</option>`;
				categorySelect.insertAdjacentHTML('beforeend', option);
			});

			categorySelect.addEventListener('change', () => {
				const selectedCategory = categorySelect.value;
				photosData.forEach(photo => {
					if (selectedCategory === '' || photo.categories.some(category => category.name === selectedCategory)) {
						photoListContainer.querySelector(`#photo-${photo.id}`).classList.remove('hidden');
					} else {
						photoListContainer.querySelector(`#photo-${photo.id}`).classList.add('hidden');
					}
				});
			});

			photosData.forEach(photo => {
				const photoCard = `
        <div class="col-md-4 mb-4" data-category="${photo.category}" id="photo-${photo.id}">
          <div class="card card-hover">
            <a href="/mygallery/show?id=${photo.id}">
              <img src="${photo.url}" class="card-img-top" alt="${photo.title}">
            </a>
            <div class="card-body">
              <h5 class="card-title">
                <a href="/mygallery/show?id=${photo.id}">${photo.title}</a>
              </h5>
              <p class="card-text">${photo.description}</p>
              <p class="card-text tags">Tags: ${photo.tag}</p>
            </div>
            <div class="p-3">
              <a href="/mygallery/show?id=${photo.id}" class="btn btn-primary">Show details</a>
            </div>
          </div>
        </div>
      `;
				photoListContainer.insertAdjacentHTML('beforeend', photoCard);
			});
		})
		.catch((err) => {
			console.error('Errore nella richiesta', err);
			alert('Errore durante la richiesta!');
		});

}

function searchPhotos() {
	const titleInput = document.querySelector('#search-input');
	const categorySelect = document.querySelector('#category-select');
	const titleFilter = titleInput.value.toUpperCase();
	const categoryFilter = categorySelect.value.toUpperCase();
	const photoListContainer = document.querySelector('#photo-list');
	const photos = photoListContainer.querySelectorAll('.col-md-4.mb-4');

	photos.forEach((photo) => {
		const name = photo.querySelector('.card-title a').textContent.toUpperCase();
		const tag = photo.querySelector('.tags').textContent.toUpperCase();
		const category = photo.dataset.category.toUpperCase();

		if ((name.includes(titleFilter) || tag.includes(titleFilter)) && (categoryFilter === '' || category === categoryFilter)) {
			photo.classList.remove('hidden');
		} else {
			photo.classList.add('hidden');
		}
	});
}


document.querySelector('#search-input').addEventListener('input', () => {
	searchPhotos();
});
