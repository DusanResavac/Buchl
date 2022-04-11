document.addEventListener('DOMContentLoaded', init);

const staticUrl = '../imgs/';

function init() {
    const loadingCircle = document.getElementById('loadingCircle'),
        url = '/api/books/with-discussions';

    loadingCircle.setAttribute('aria-hidden', 'false');
    loadingCircle.classList.remove('visually-hidden');

    //localStorage.setItem('bookFavourites', JSON.stringify([1,2,3]));
    let httpRequest = new XMLHttpRequest(),
        bookFavouritesLS = localStorage.getItem('bookFavourites');
    
    httpRequest.open('POST', url, true);
    httpRequest.setRequestHeader('Content-type', 'application/json');
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            let bookDiscussions = JSON.parse(httpRequest.response);

            for (let i = 0; i < bookDiscussions.length; i++) {
                let bookDiscussion = bookDiscussions[i],
                    article = document.createElement('article'),
                    divBook = document.createElement('div'),
                    divDiscussions = document.createElement('div'),
                    imgBook = document.createElement('img'),
                    bookInfoDiv = document.createElement('div'),
                    h3Title = document.createElement('h3'),
                    titleA = document.createElement('a'),
                    authorA = document.createElement('a'),
                    authorDiv = document.createElement('div'),
                    authorImg = document.createElement('img'),
                    authorName = document.createElement('p'),
                    ul = document.createElement('ul');

                if (i === 0) {
                    let h2 = document.createElement('h2');
                    h2.classList.add('is-size-4');
                    h2.classList.add('mb-5');
                    h2.innerText = 'Diskussionen zu deinen Favoriten';
                    article.appendChild(h2);
                }

                article.appendChild(divBook);
                article.appendChild(divDiscussions);
                divBook.appendChild(imgBook);
                divBook.classList.add('div-book');
                divBook.appendChild(bookInfoDiv);
                bookInfoDiv.appendChild(h3Title);
                bookInfoDiv.appendChild(authorA);
                bookInfoDiv.classList.add('book-info-div');
                h3Title.appendChild(titleA);
                h3Title.classList.add('is-size-5');
                authorA.appendChild(authorDiv);
                authorDiv.appendChild(authorImg);
                authorDiv.appendChild(authorName);
                authorDiv.classList.add('author-div');

                imgBook.setAttribute('src', bookDiscussion.imageLink === null ?
                    staticUrl + 'image-preview.png' :
                    staticUrl + bookDiscussion.imageLink);
                imgBook.setAttribute('alt', bookDiscussion.imageAlt === null ?
                    bookDiscussion.title :
                    bookDiscussion.imageAlt);
                imgBook.setAttribute('loading', 'lazy');
                titleA.setAttribute('href', '/book/' + bookDiscussion.id);
                titleA.innerText = bookDiscussion.title;
                authorA.setAttribute('href', '/books?author=' + bookDiscussion.author.id);
                authorImg.setAttribute('src', bookDiscussion.author.imageLink === null ?
                    staticUrl + 'user.png' :
                    staticUrl + bookDiscussion.author.imageLink);
                authorImg.setAttribute('alt', bookDiscussion.author.imageAlt === null ?
                    bookDiscussion.author.firstName + ' ' + bookDiscussion.author.lastName :
                    bookDiscussion.author.imageAlt);
                authorName.innerText = bookDiscussion.author.firstName + ' ' + bookDiscussion.author.lastName;

                for (let b = 0; b < bookDiscussion.discussions.length; b++) {
                    let discussion = bookDiscussion.discussions[b],
                        dLi = document.createElement('li'),
                        dArticle = document.createElement('article'),
                        dATitle = document.createElement('a'),
                        dAAnswers = document.createElement('a'),
                        dDivUser = document.createElement('div'),
                        dDivContent = document.createElement('div'),
                        dImg = document.createElement('img'),
                        dSpanNickname = document.createElement('span'),
                        dH4 = document.createElement('h4');

                    divDiscussions.appendChild(ul);
                    ul.appendChild(dLi);
                    dLi.appendChild(dArticle);
                    divDiscussions.classList.add('book-discussions');
                    dArticle.appendChild(dDivUser);
                    dArticle.appendChild(dDivContent);
                    dArticle.classList.add('book-discussion');
                    dDivUser.appendChild(dImg);
                    dDivUser.appendChild(dSpanNickname);
                    dDivUser.classList.add('book-discussion-user');
                    dDivContent.appendChild(dH4);
                    dDivContent.appendChild(dAAnswers);
                    dDivContent.classList.add('book-discussion-content');
                    dH4.appendChild(dATitle);


                    dATitle.innerText = discussion.title;
                    dATitle.setAttribute('href', '/discussion/' + discussion.id);
                    dAAnswers.setAttribute('href', '/discussion/' + discussion.id);
                    dImg.setAttribute('src', discussion.user.image === null ?
                        staticUrl + 'user.png' :
                        discussion.user.image);
                    dImg.setAttribute('alt', '');
                    dImg.setAttribute('loading', 'lazy');
                    dSpanNickname.innerText = discussion.user.nickname;
                    dAAnswers.innerText = discussion.comments.length +
                        (discussion.comments.length === 1 ? ' Antwort' : ' Antworten');
                }

                document.getElementById('main').appendChild(article);
            }

            loadingCircle.setAttribute('aria-hidden', 'true');
            loadingCircle.classList.add('visually-hidden');
            document.getElementById('main').setAttribute('aria-busy', 'false');
        }
    }
    httpRequest.send(bookFavouritesLS === null ? '[]' : bookFavouritesLS);
}