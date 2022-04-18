document.addEventListener('DOMContentLoaded', init);

const staticUrl = '../imgs/';

let submitButton,
    formElements;


function init() {
    submitButton = document.getElementById('submitButton');
    formElements = document.getElementsByClassName('form-data');

    const tag = document.getElementById('tag'),
        rating = document.getElementById('rating'),
        author = document.getElementById('author'),
        releaseYearFrom = document.getElementById('releaseYearFrom'),
        releaseYearUntil = document.getElementById('releaseYearUntil'),
        search = document.getElementById('search');


    document.getElementById('form').addEventListener('submit', function (ev) {


        const loadingCircle = document.getElementById('loadingCircle'),
            noSearchResults = document.getElementById('noSearchResults');

        loadingCircle.classList.remove('is-hidden');
        noSearchResults.classList.add('is-hidden');

        submitButton.setAttribute('disabled', '');
        submitButton.setAttribute('aria-busy', 'true');
        const searchResults = document.getElementById('searchResults'),
            baseUrl = '/api/books/search';
        let url = baseUrl + '?';

        searchResults.setAttribute('aria-busy', 'true');
        let articles = searchResults.getElementsByClassName('article-wrapper');
        for (let i = 0; i < articles.length; i++) {
            articles[i].parentNode.removeChild(articles[i]);
        }

        let httpRequest = new XMLHttpRequest();

        for (let i = 0; i < formElements.length; i++) {
            if (i === 0) {
                url += formElements[i].getAttribute('name') + '=' + formElements[i].value;
            } else {
                url += '&' + formElements[i].getAttribute('name') + '=' + formElements[i].value;
            }
        }

        httpRequest.open('GET', url, true);
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState === 4 && httpRequest.status === 200) {
                let books = JSON.parse(httpRequest.response),
                    articleWrapper = document.createElement('ul');
                articleWrapper.classList.add('article-wrapper');

                for (let i = 0; i < books.length; i++) {
                    let book = books[i],
                        article = document.createElement('article'),
                        articleLi = document.createElement('li'),
                        a = document.createElement('a'),
                        divWrapper = document.createElement('div'),
                        img = document.createElement('img'),
                        divArticleContent = document.createElement('div'),
                        h3 = document.createElement('h3'),
                        divFooter = document.createElement('div'),
                        pYear = document.createElement('p'),
                        imgRating = document.createElement(book.averageRating === null ? 'p' : 'img'),
                        pAuthorName = document.createElement('p');

                    articleWrapper.appendChild(articleLi);
                    articleLi.appendChild(article);
                    article.classList.add('book-overview');
                    article.appendChild(a);
                    a.setAttribute('href', '/book/' + book.id);
                    a.appendChild(divWrapper);
                    divWrapper.appendChild(img);
                    divWrapper.appendChild(divArticleContent);
                    divWrapper.classList.add('article-link-wrapper');
                    img.setAttribute('src', book.imageLink === null ? staticUrl + 'image-preview.png' : staticUrl + book.imageLink);
                    img.setAttribute('loading', 'lazy');
                    img.setAttribute('alt', book.imageAlt === null ? '' : book.imageAlt);
                    divArticleContent.appendChild(h3);
                    divArticleContent.appendChild(divFooter);
                    divArticleContent.classList.add('article-content');
                    h3.innerText = book.title;
                    divFooter.appendChild(pYear);
                    divFooter.appendChild(imgRating);
                    divFooter.appendChild(pAuthorName);
                    divFooter.classList.add('article-footer');
                    pYear.innerText = book.releaseDate === null ? ' - ' : new Date(book.releaseDate).getFullYear();
                    //pYear.setAttribute('aria-label', 'Im Jahr ' + pYear.innerText + ' erschienen');
                    if (book.averageRating === null) {
                        imgRating.innerText = 'Noch nicht erschienen';
                    } else {
                        imgRating.setAttribute('src', staticUrl + 'SVG/rating-' + Math.round(book.averageRating) + '.svg');
                        imgRating.setAttribute('alt', book.averageRating + " von 5 Sterne");
                    }
                    if (book.author === null) {
                        pAuthorName.innerText = "Unbekannt";
                    } else {
                        if (book.author.lastName === null || book.author.lastName === "") {
                            pAuthorName.innerText = book.author.firstName === null ? 'Unbekannt' : book.author.firstName;
                        } else {
                            pAuthorName.innerText = book.author.lastName;
                        }
                    }
                }

                searchResults.appendChild(articleWrapper);
                loadingCircle.classList.add('is-hidden');
                if (books.length === 0) {
                    noSearchResults.classList.remove('is-hidden');
                }
                submitButton.removeAttribute('disabled');
                submitButton.setAttribute('aria-busy', 'false');
                searchResults.setAttribute('aria-busy', 'false');
            }
        }
        httpRequest.send();
        window.history.pushState(null, '',
            '/books?tag=' + tag.value +
            '&rating=' + rating.value +
            '&author=' + author.value +
            '&releaseYearFrom=' + releaseYearFrom.value +
            '&releaseYearUntil=' + releaseYearUntil.value +
            '&q=' + search.value);
        ev.preventDefault();

    });
}