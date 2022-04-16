document.addEventListener('DOMContentLoaded', init);

const staticUrl = '../imgs/';

function init() {
    const favourites = localStorage.getItem('bookFavourites') === null ? [] : JSON.parse(localStorage.getItem('bookFavourites'));
    const recommendationsSection = document.getElementById('recommendations');
    const main = document.getElementById('main');
    const httpRequest = new XMLHttpRequest();
    const url = '/api/recommendations';

    httpRequest.open('POST', url, true);
    httpRequest.setRequestHeader('Content-type', 'application/json');
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            let response = JSON.parse(httpRequest.response);
            for (let i = 0; i < response.length; i++) {
                let book = response[i],
                    h2 = document.createElement('h2'),
                    recommendationsForSingleBookWrapper = document.createElement('div'),
                    recommendationsForSingleBook = document.createElement('div');

                recommendationsForSingleBookWrapper.classList.add('recommendation-wrapper');
                recommendationsForSingleBookWrapper.appendChild(h2);
                recommendationsForSingleBookWrapper.appendChild(recommendationsForSingleBook);
                recommendationsForSingleBook.classList.add('recommendation');
                h2.innerHTML = 'Andere, die <a href="/book/' + book.bookId + '">'  + book.bookTitle + '</a> mochten, mögen auch:'


                for (let b = 0; b  < book.recommendations.length; b++) {
                    let recommendation = book.recommendations[b],
                        singleRecommendation = document.createElement('div'),
                        imgBook = document.createElement('img'),
                        bookInfo = document.createElement('div'),
                        h3 = document.createElement('h3'),
                        titleA = document.createElement('a'),
                        divRating = document.createElement('div'),
                        spanRating = document.createElement('span'),
                        imgRating = document.createElement('img');

                    recommendationsForSingleBook.appendChild(singleRecommendation);
                    singleRecommendation.classList.add('book');
                    singleRecommendation.appendChild(imgBook);
                    singleRecommendation.appendChild(bookInfo);
                    bookInfo.classList.add('book-info');
                    bookInfo.appendChild(h3);
                    bookInfo.appendChild(divRating);
                    h3.appendChild(titleA);
                    divRating.appendChild(spanRating);
                    divRating.appendChild(imgRating);

                    imgBook.setAttribute('src', recommendation.imageLink === null ?
                        staticUrl + 'image-preview.png' :
                        staticUrl + recommendation.imageLink);
                    imgBook.setAttribute('alt', recommendation.imageAlt === null ?
                        '' :
                        recommendation.imageAlt);
                    titleA.setAttribute('href', '/book/' + recommendation.id);
                    titleA.innerText = recommendation.title;

                    if (book.averageRating === null) {
                        spanRating.innerText = 'Noch nicht erschienen';
                    } else {
                        spanRating.innerText = recommendation.averageRating;
                        imgRating.setAttribute('src', staticUrl + 'SVG/rating-' + Math.round(recommendation.averageRating) + '.svg');
                        imgRating.setAttribute('alt', recommendation.averageRating + " von 5 Sterne");
                    }
                }

                recommendationsSection.appendChild(recommendationsForSingleBookWrapper);
            }

            if (response.length === 0) {
                const p = document.createElement('p');
                p.innerHTML = 'Du hast noch keine Favoriten. Füge Bücher zu deinen Favoriten hinzu, um Buchvorschläge zu erhalten! Du kannst ein Buch auf der Buchseite als Favorit markieren.';
                recommendationsSection.appendChild(p);
            }
            main.setAttribute('aria-busy', 'false');
        }
    };

    httpRequest.send(JSON.stringify(favourites));
}