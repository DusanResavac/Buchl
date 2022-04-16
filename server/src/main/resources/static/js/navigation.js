document.addEventListener('DOMContentLoaded', init);

let expandableMenus;

function init() {
    expandableMenus = document.getElementsByClassName('expandable-menu');

    document.getElementById('skipNavigation').addEventListener('click', function () {
        document.getElementById('main').focus();
    });

    document.addEventListener('keydown', function (ev) {
        if (ev.key === 'Escape' || ev.code === 'Escape' || ev.key === 'Esc') {
            console.log('document - keydown escape');
            for (let i = 0; i < expandableMenus.length; i++) {
                let menu = expandableMenus[i],
                    subMenu = document.getElementById(menu.getAttribute('data-menu'));
                closeMenu(menu, subMenu);
            }
        }
    });

    for (let i = 0; i < expandableMenus.length; i++) {
        let menu = expandableMenus[i],
            subMenu = document.getElementById(menu.getAttribute('data-menu'));
        let subMenuItems = document.querySelectorAll("#" + menu.getAttribute('data-menu') + " > li > a");

        menu.addEventListener('focus', function () {
            console.log('menu - focus');
            expandMenu(menu, subMenu);
        });
        /*menu.addEventListener('keydown', function (ev) {
            console.log('menu - keydown');
            if ((ev.key === "Tab" || ev.code === "Tab") &&
                ev.shiftKey === false &&
                menu.getAttribute('aria-expanded') === 'true') {
                ev.preventDefault();
                expandMenu(menu);
                subMenuItems[0].focus();
            }
        });*/
        menu.addEventListener('mouseenter', function () {
            console.log('menu - mouseenter');
            expandMenu(menu, subMenu);
        });
        menu.addEventListener('touchstart', function () {
            console.log('menu - touchstart');
            expandMenu(menu, subMenu);
            document.getElementsByTagName('body')[0].addEventListener('touchstart', function touchStartDocument(ev) {
                console.log('body touchstart');
                if (!menu.contains(ev.target) && !subMenu.contains(ev.target)) {
                    closeMenu(menu, subMenu);
                    this.removeEventListener('touchstart', touchStartDocument);
                }
            });
        });
        subMenu.addEventListener('mouseenter', function () {
            console.log('subMenu - mouseenter');
            expandMenu(menu, subMenu);
        });
        subMenu.addEventListener('mouseleave', function () {
            console.log('subMenu - mouseleave');
            closeMenu(menu, subMenu);
        });
        menu.addEventListener('mouseleave', function () {
            console.log('menu - mouseleave');
            closeMenu(menu, subMenu);
        });
        menu.addEventListener('blur', function (ev) {
            console.log('menu - blur');
            // IE 11
            let relTarget = ev.relatedTarget || document.activeElement;

            if (relTarget === null ||
                relTarget.getAttribute('data-parentmenu') !== ev.target.getAttribute('data-menu')) {
                closeMenu(menu, subMenu);
            }
        });

        for (let b = 0; b < subMenuItems.length; b++) {
            let subMenuItem = subMenuItems[b];

            subMenuItem.addEventListener('blur', function (ev) {
                console.log('subMenuItem - blur');
                let t = ev.relatedTarget || document.activeElement;

                if (t === null) {
                    return;
                }

                let tMenu = t.getAttribute('data-menu'),
                    tParentMenu = t.getAttribute('data-parentmenu'),
                    parentMenu = subMenuItem.getAttribute('data-parentmenu');

                if (tMenu !== parentMenu && tParentMenu !== parentMenu) {
                    closeMenu(menu, subMenu);
                }
            });
        }
    }
}

function expandMenu(menu, subMenu) {
    console.log("expanding");
    subMenu.classList.remove('is-hidden');
    menu.setAttribute('aria-expanded', 'true');
    console.log("expanded");
}

function closeMenu(menu, subMenu) {
    console.log("closing");
    subMenu.classList.add('is-hidden');
    menu.setAttribute('aria-expanded', 'false');
    console.log("closed");
}