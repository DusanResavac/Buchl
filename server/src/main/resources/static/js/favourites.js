document.addEventListener('DOMContentLoaded', init);

const staticUrl = '../imgs/';

function init() {
    let favourites = localStorage.getItem('bookFavourites') === null ? [] : JSON.parse(localStorage.getItem('bookFavourites'));
    const allFavourites =  document.getElementById('allFavourites');
    const main = document.getElementById('main');
    const httpRequest = new XMLHttpRequest();
    const url = '/api/books-short';

    httpRequest.open('POST', url, true);
    httpRequest.setRequestHeader('Content-type', 'application/json');
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            let response = JSON.parse(httpRequest.response);

            for (let i = 0; i < response.length; i++) {
                let book = response[i],
                    article = document.createElement('article'),
                    heartButton = document.createElement('button'),
                    heartButtonImg = document.createElement('img'),
                    img = document.createElement('img'),
                    h2 = document.createElement('h2');

                allFavourites.appendChild(article);
                article.setAttribute('id', 'book' + book.id);
                article.appendChild(heartButton);
                article.appendChild(img);
                article.appendChild(h2);
                heartButton.classList.add('heartButton');
                heartButton.appendChild(heartButtonImg);
                heartButton.setAttribute('data-book', 'book' + book.id);
                heartButton.setAttribute('title', book.title + ' aus den Favoriten entfernen');
                heartButton.addEventListener('click', function (ev) {
                    let id = 'book' + book.id,
                        element = document.getElementById(id);

                    element.parentNode.removeChild(element);
                    favourites.splice(favourites.indexOf(book.id), 1);
                    localStorage.setItem('bookFavourites', JSON.stringify(favourites));

                    if (allFavourites.children.length === 0) {
                        const p = document.createElement('p');
                        p.innerHTML = 'Du hast noch keine Favoriten. Füge Bücher zu deinen Favoriten hinzu, um Buchvorschläge zu erhalten! Du kannst ein Buch auf der Buchseite als Favorit markieren.';
                        allFavourites.appendChild(p);
                    }
                });

                heartButtonImg.setAttribute('src', staticUrl + 'SVG/heart-red.svg');
                //heartButtonImg.setAttribute('aria-label', 'Als Favorit entfernen');
                heartButtonImg.setAttribute('alt', '');

                img.setAttribute('src', book.imageLink === null ? staticUrl + 'image-preview.png' : staticUrl + book.imageLink);
                img.setAttribute('loading', 'lazy');
                img.setAttribute('alt', book.imageAlt === null ? book.title : book.imageAlt);

                h2.innerHTML = '<a href="book/' + book.id + '">' + book.title + '</a>';

            }

            if (response.length === 0) {
                const p = document.createElement('p');
                p.innerHTML = 'Du hast noch keine Favoriten. Füge Bücher zu deinen Favoriten hinzu, um Buchvorschläge zu erhalten! Du kannst ein Buch auf der Buchseite als Favorit markieren.';
                allFavourites.appendChild(p);
            }

            main.setAttribute('aria-busy', 'false');
        }
    }

    httpRequest.send(JSON.stringify(favourites));
}