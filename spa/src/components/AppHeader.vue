<template>
  <fragment>
    <header class="has-text-centered">
      <span id="title" tabindex="-1">Buchl</span>
    </header>
    <a class="visually-hidden" id="skipNavigation" ref="skipNavigation" v-bind:href="mainId">Navigation überspringen</a>
    <nav id="main-nav" ref="mainNav" class="mt-5" aria-label="Hauptnavigation">
      <ul>
        <li>
          <router-link class="p-3 is-size-4-desktop" :aria-current="activeLink === 'home' ? 'page' : 'false'" v-bind:class="isActiveLink('home')" aria-haspopup="false" to="/">Startseite</router-link>
        </li>
        <li >
          <router-link class="p-3 is-size-4-desktop" :aria-current="activeLink === 'favourites' ? 'page' : 'false'" v-bind:class="isActiveLink('favourites')" aria-haspopup="false" to="/favourites">Favoriten</router-link>
        </li>
        <li >
          <!--          <router-link class="p-3 is-size-4-desktop expandable-menu" data-menu="books-menu"
                                 :aria-current="activeLink === 'books' ? 'page' : 'false'"
                                 v-bind:class="isActiveLink('books')"
                                 aria-haspopup="true"
                                 aria-expanded="false"
                                 to="/books">Bücher</router-link>-->
          <button class="button is-ghost expandable-menu p-3 is-size-4-desktop"
                  v-bind:class="isActiveLink('books')"
                  :aria-current="activeLink === 'books' ? 'page' : 'false'"
                  data-menu="books-menu"
                  aria-haspopup="true"
                  aria-expanded="false">Bücher</button>
          <ul aria-label="Bücher" class="is-hidden" id="books-menu">
            <li >
              <router-link data-parentmenu="books-menu" tabindex="0" to="/books">Stöbern und Filtern</router-link>
            </li>
            <li >
              <router-link data-parentmenu="books-menu" tabindex="0" to="/tags">Themen und Genres</router-link>
            </li>
          </ul>
        </li>
        <li >
          <router-link class="p-3 is-size-4-desktop" :aria-current="activeLink === 'discussions' ? 'page' : 'false'" v-bind:class="isActiveLink('discussions')" aria-haspopup="false" to="/discussions">Diskussionen</router-link>
        </li>
        <li >
          <router-link class="p-3 is-size-4-desktop" :aria-current="activeLink === 'reviews' ? 'page' : 'false'" v-bind:class="isActiveLink('reviews')" aria-haspopup="false" to="/reviews">Rezensionen</router-link>
        </li>
        <li >
          <router-link class="p-3 is-size-4-desktop" :aria-current="activeLink === 'recommendations' ? 'page' : 'false'" v-bind:class="isActiveLink('recommendations')" aria-haspopup="false" to="/recommendations">Vorschläge</router-link>
        </li>
      </ul>
    </nav>
  </fragment>
</template>

<script>
export default {
  name: 'AppHeader',
  props: ['mainId', 'activeLink'],
  mounted() {
    const expandableMenus = this.$refs.mainNav.getElementsByClassName('expandable-menu');
    this.$refs.skipNavigation.addEventListener('click', () => {
      document.querySelector(this.mainId).focus();
    });
    this.addDocumentEventListener(expandableMenus);
    this.addEventListenerToMenuAndSubmenu(expandableMenus);
  },
  methods: {
    isActiveLink(linkName) {
      return {
        active: this.activeLink === linkName,
      };
    },
    addDocumentEventListener(expandableMenus) {
      document.addEventListener('keydown', (ev) => {
        if (ev.key === 'Escape' || ev.code === 'Escape' || ev.key === 'Esc') {
          // console.log('document - keydown escape');
          for (let i = 0; i < expandableMenus.length; i += 1) {
            // console.log(this.$refs.mainNav);
            const menu = expandableMenus[i];
            const subMenu = this.$refs.mainNav.querySelector(`#${menu.getAttribute('data-menu')}`);
            this.closeMenu(menu, subMenu);
          }
        }
      });
    },
    addEventListenerToMenuAndSubmenu(expandableMenus) {
      for (let i = 0; i < expandableMenus.length; i += 1) {
        const menu = expandableMenus[i];
        const subMenu = this.$refs.mainNav.querySelector(`#${menu.getAttribute('data-menu')}`);
        const subMenuItems = this.$refs.mainNav.querySelectorAll(`#${menu.getAttribute('data-menu')} > li > a`);

        menu.addEventListener('focus', () => {
          // this.expandMenu(menu, subMenu);
        });
        menu.addEventListener('mouseenter', (ev) => {
          // console.log('menu - mouseenter', ev);
          const relTarget = ev.relatedTarget;
          // chrome triggers mouseenter event on touch devices
          if (relTarget !== null) {
            this.expandMenu(menu, subMenu);
          }
        });
        const self = this;
        document.getElementsByTagName('body')[0].addEventListener('touchstart', function touchStartDocument(ev) {
          // console.log('body touchstart');
          if (!menu.contains(ev.target) && !subMenu.contains(ev.target)) {
            self.closeMenu(menu, subMenu);
            this.removeEventListener('touchstart', touchStartDocument);
          }
        });
        menu.addEventListener('click', () => {
          // console.log('menu - click');
          if (menu.getAttribute('aria-expanded') === 'true') {
            this.closeMenu(menu, subMenu);
          } else {
            this.expandMenu(menu, subMenu);
          }
        });
        subMenu.addEventListener('mouseenter', () => {
          // console.log('subMenu - mouseenter');
          this.expandMenu(menu, subMenu);
        });
        subMenu.addEventListener('mouseleave', () => {
          // console.log('subMenu - mouseleave');
          this.closeMenu(menu, subMenu);
        });
        menu.addEventListener('mouseleave', () => {
          // console.log('menu - mouseleave');
          this.closeMenu(menu, subMenu);
        });
        menu.addEventListener('blur', (ev) => {
          // console.log('menu - blur');
          // IE 11
          const relTarget = ev.relatedTarget || document.activeElement;

          if (relTarget === null
              || relTarget.getAttribute('data-parentmenu') !== ev.target.getAttribute('data-menu')) {
            this.closeMenu(menu, subMenu);
          }
        });

        for (let b = 0; b < subMenuItems.length; b += 1) {
          const subMenuItem = subMenuItems[b];

          subMenuItem.addEventListener('blur', (ev) => {
            // console.log('subMenuItem - blur');
            const t = ev.relatedTarget || document.activeElement;

            if (t === null) {
              return;
            }

            const tMenu = t.getAttribute('data-menu');
            const tParentMenu = t.getAttribute('data-parentmenu');
            const parentMenu = subMenuItem.getAttribute('data-parentmenu');

            if (tMenu !== parentMenu && tParentMenu !== parentMenu) {
              this.closeMenu(menu, subMenu);
            }
          });
        }
      }
    },
    expandMenu(menu, subMenu) {
      // console.log('expanding');
      subMenu.classList.remove('is-hidden');
      menu.setAttribute('aria-expanded', 'true');
      // console.log('expanded');
    },
    closeMenu(menu, subMenu) {
      // console.log('closing');
      subMenu.classList.add('is-hidden');
      menu.setAttribute('aria-expanded', 'false');
      // console.log('closed');
    },
  },
  computed: {
  },
};
</script>

<style>

* {
  box-sizing: border-box;
}

.visually-hidden {
  position: absolute;
}

.visually-hidden:not(:focus):not(:active) {
  clip: rect(1px, 1px, 1px, 1px);
  clip-path: inset(50%);
  height: 1px;
  width: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute;
}

</style>

<style scoped>

header {
  background-image: url("./../assets/imgs/header-background.jpg");
  background-position: center;
}

#title {
  font-size: 6em;
  font-weight: bold;
  color: #f9f9f9;
  -webkit-text-stroke: 3px #262626;
}

#main-nav {
  text-align: center;
  margin: 1.5rem 6% 0 6%;
}

#main-nav > ul  {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  border: 1px solid #485fc7;
  padding: 10px 20px 10px 20px;
}

#main-nav > ul > li {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

#main-nav > ul > li > ul {
  z-index: 10;
  position: absolute;
  display: block;
  background-color: #f9f9f9;
  box-shadow: 0px 0px 5px -2px rgba(82,82,82,1);
  border-radius: 5px;
  padding: 0.5em;
  left: 0;
  bottom: 0;
  transform: translateY(100%);
}

#main-nav > ul > li > ul a {
  min-width: 11em;
  text-align: left;
  padding: 1em;
}

#main-nav > ul a {
  display: inline-block;
}

a:focus {
  border: 2px dashed #485fc7;
  outline: none;
}

#main-nav a.active, #main-nav button.active {
  font-weight: bold;
}

#main-nav button::after {
  content: '';
  background-image: url("./../assets/imgs/leftArrow.svg");
  width: 18px;
  height: 18px;
  background-size: contain;
  position: absolute;
  right: 6px;
  transform: rotate(0);
  transition: transform linear 100ms;
}

#main-nav button {
  position: relative;
  padding-right: 30px !important;
}

#main-nav button[aria-expanded=true]::after {
  transform: rotate(-90deg);
}

#title {
  text-transform: uppercase;
}

/*#main-nav ul.is-hidden {
  display: block !important;
  position: static;
}*/

@media all and (min-width: 500px) {
  #main-nav {
    margin: 1.5rem auto 0 auto;
    max-width: 1100px;
  }

  #main-nav > ul {
    flex-wrap: wrap;
    flex-direction: row;
  }

  /*  #main-nav ul.is-hidden {
      display: none !important;
      position: absolute;
    }*/
}

</style>
