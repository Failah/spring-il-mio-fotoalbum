//Una foto contiene almeno le seguenti informazioni :
//- titolo
//- descrizione
//- url
//- tag
//- visibile
//- categorie
//Una foto può essere collegata a più categorie, e una categoria può essere collegata a più foto.
//Prevedere quindi anche una semplice pagina di creazione, modifica, cancellazione categorie.

package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "photos")
public class Photo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Field title cannot be empty")
	@Size(min = 1, max = 50, message = "Title cannot have more than {max} characters")
	private String title;

	private String description;

	@NotEmpty(message = "Image url cannot be empty")
	private String url;

	private String tag;

	private boolean isVisible;

	@ManyToMany
	private List<Category> categories;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
