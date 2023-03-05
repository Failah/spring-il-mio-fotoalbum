console.log("Index: JS OK!");

photoList();

// Aggiungi questo codice al metodo photoList()
function photoList() {
  axios.get('http://localhost:8080/api/photos')
    .then((res) => {
		console.log(typeof res.data);
      const photoListContainer = document.querySelector('#photo-list');
      res.data.forEach(photo => {
        const photoCard = `
            <div class="col-md-4 mb-4">
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

      const categorySelect = document.querySelector('#category-select');
      const categories = res.data.map(photo => photo.category).filter((category, index, self) => self.indexOf(category) === index);
      categories.forEach(category => {
        const option = `<option value="${category}">${category}</option>`;
        categorySelect.insertAdjacentHTML('beforeend', option);
      });

      categorySelect.addEventListener('change', () => {
        const selectedCategory = categorySelect.value;
        photos.forEach(photo => {
          if (selectedCategory === '' || photo.category === selectedCategory) {
            photo.classList.remove('hidden');
          } else {
            photo.classList.add('hidden');
          }
        });
      });
    })
    .catch((err) => {
      console.error('errore nella richiesta', err);
      alert('Errore durante la richiesta!');
    });
}

function searchPhotos() {
  const input = document.querySelector('#search-input');
  const filter = input.value.toUpperCase();
  const photoListContainer = document.querySelector('#photo-list');
  const photos = photoListContainer.querySelectorAll('.col-md-4.mb-4');

  photos.forEach((photo) => {
    const name = photo.querySelector('.card-title a').textContent.toUpperCase();
    const tag = photo.querySelector('.tags').textContent.toUpperCase();

    if (name.includes(filter) || tag.includes(filter)) {
      photo.classList.remove('hidden');
    } else {
      photo.classList.add('hidden');
    }
  });
}

document.querySelector('#search-input').addEventListener('input', () => {
  searchPhotos();
});
