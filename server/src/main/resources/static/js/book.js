document.addEventListener('DOMContentLoaded', init);

let favouriteButton;

function init() {
    favouriteButton = document.getElementById('favouriteButton');
    const id = bookId;
    const favourites = localStorage.getItem('bookFavourites') === null ? [] : JSON.parse(localStorage.getItem('bookFavourites'));
    const index = favourites.indexOf(id);

    if (index >= 0) {
        favouriteButton.innerText = "Favorit entfernen";
    }

    favouriteButton.addEventListener('click', function () {
        addOrRemoveFromFavourites(id);
    });
}

function addOrRemoveFromFavourites(id) {
    const favourites = localStorage.getItem('bookFavourites') === null ? [] : JSON.parse(localStorage.getItem('bookFavourites'));
    const index = favourites.indexOf(id);

    if (index >= 0) {
        favourites.splice(index, 1);
        favouriteButton.innerText = "Als Favorit hinzufügen";
    } else {
        favourites.push(id);
        favouriteButton.innerText = "Favorit entfernen";
    }

    localStorage.setItem('bookFavourites', JSON.stringify(favourites));
}