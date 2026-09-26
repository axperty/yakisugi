import { defineConfig } from 'vitepress'
import { tabsMarkdownPlugin } from 'vitepress-plugin-tabs'

const sharedSidebar = [
  {
    text: 'Wiki',
    items: [
      { text: 'About', link: '/wiki/about' },
      { text: 'Development Process', link: '/wiki/development-process' },
      { text: 'Installation', link: '/wiki/installation' },
      {
        text: 'Getting Started',
        collapsed: true,
        items: [
          { text: 'Charred & Slightly Charred Planks', link: '/wiki/getting-started/charred-planks' }
        ]
      },
      {
        text: 'Materials',
        collapsed: true,
        items: [
          { text: 'Iron Sand', link: '/wiki/materials/iron-sand' },
          { text: 'Tamahagane Chunk', link: '/wiki/materials/tamahagane-chunk' },
          { text: 'Wheat Straw', link: '/wiki/materials/wheat-straw' }
        ]
      },
      {
        text: 'Decorations',
        collapsed: true,
        items: [
          { text: 'Aged Stone', link: '/wiki/decorations/aged-stone' },
          { text: 'Bamboo Cladding Wall', link: '/wiki/decorations/bamboo' },
          { text: "Cabinets for Farmer's Delight", link: '/wiki/decorations/cabinets' },
          { text: 'Paper Lantern', link: '/wiki/decorations/paper-lantern' },
          { text: 'Shoji', link: '/wiki/decorations/shoji' },
          { text: 'Stone Lantern', link: '/wiki/decorations/stone-lantern' },
          { text: 'Tatami', link: '/wiki/decorations/tatami' },
          { text: 'Tsukubai', link: '/wiki/decorations/tsukubai' }
        ]
      },
      {
        text: 'Weapons & Armor',
        collapsed: true,
        items: [
          { text: 'Katanas & Shurikens', link: '/wiki/weapons-and-armor/katanas-and-shurikens' },
          { text: 'Straw Armor', link: '/wiki/weapons-and-armor/straw-armor' }
        ]
      },
      { text: 'Translations', link: '/wiki/translations' },
      { text: 'Feedback & Suggestions', link: '/wiki/feedback' }
    ]
  }
];

function getSidebar(prefix) {
  const sidebar = JSON.parse(JSON.stringify(sharedSidebar));
  function appendPrefix(items) {
    items.forEach(item => {
      if (item.link) item.link = prefix + item.link;
      if (item.items) appendPrefix(item.items);
    });
  }
  appendPrefix(sidebar);
  return sidebar;
}

export default defineConfig({
  base: '/yakisugi/',

  markdown: {
    config(md) {
      md.use(tabsMarkdownPlugin)
    }
  },
  title: "Yakisugi",
  description: "Yakisugi is a Minecraft mod inspired by traditional Japanese culture, adding charred wood, katanas, shurikens, straw armor, and decorations like shoji screens and stone lanterns.",
  head: [
    ['link', { rel: 'icon', href: '/yakisugi/assets/yakisugi_icon.png' }],
    ['meta', { name: 'theme-color', content: '#d97706' }],
    ['meta', { property: 'og:title', content: 'Yakisugi' }],
    ['meta', { property: 'og:description', content: 'Yakisugi is a Minecraft mod inspired by traditional Japanese culture, adding charred wood, katanas, shurikens, straw armor, and decorations like shoji screens and stone lanterns.' }],
    ['meta', { property: 'og:image', content: 'https://axperty.com/yakisugi/assets/yakisugi_hero.png' }],
    ['meta', { name: 'twitter:card', content: 'summary_large_image' }],
    ['meta', { name: 'twitter:image', content: 'https://axperty.com/yakisugi/assets/yakisugi_hero.png' }]
  ],
  sitemap: {
    hostname: 'https://axperty.com/yakisugi/'
  },

  locales: {
    root: {
      label: 'English',
      lang: 'en'
    },
    ja: {
      label: 'Japanese',
      lang: 'ja',
      themeConfig: {
        nav: [
          { text: 'Home', link: '/ja/' },
          { text: 'Wiki', link: '/ja/wiki/about' },
          { text: 'Donate', link: '/donate' }
        ],
        sidebar: {
          '/ja/wiki/': getSidebar('/ja')
        }
      }
    },
    es: {
      label: 'Spanish',
      lang: 'es',
      themeConfig: {
        nav: [
          { text: 'Home', link: '/es/' },
          { text: 'Wiki', link: '/es/wiki/about' },
          { text: 'Donate', link: '/donate' }
        ],
        sidebar: {
          '/es/wiki/': getSidebar('/es')
        }
      }
    },
    zh: {
      label: 'Chinese',
      lang: 'zh',
      themeConfig: {
        nav: [
          { text: 'Home', link: '/zh/' },
          { text: 'Wiki', link: '/zh/wiki/about' },
          { text: 'Donate', link: '/donate' }
        ],
        sidebar: {
          '/zh/wiki/': getSidebar('/zh')
        }
      }
    },
    ko: {
      label: 'Korean',
      lang: 'ko',
      themeConfig: {
        nav: [
          { text: 'Home', link: '/ko/' },
          { text: 'Wiki', link: '/ko/wiki/about' },
          { text: 'Donate', link: '/donate' }
        ],
        sidebar: {
          '/ko/wiki/': getSidebar('/ko')
        }
      }
    },
    ru: {
      label: 'Russian',
      lang: 'ru',
      themeConfig: {
        nav: [
          { text: 'Home', link: '/ru/' },
          { text: 'Wiki', link: '/ru/wiki/about' },
          { text: 'Donate', link: '/donate' }
        ],
        sidebar: {
          '/ru/wiki/': getSidebar('/ru')
        }
      }
    }
  },

  themeConfig: {
    search: {
      provider: 'local'
    },
    logo: '/assets/yakisugi_icon.png',
    nav: [
      { text: 'Home', link: '/' },
      { text: 'Wiki', link: '/wiki/about' },
      { text: 'Donate', link: '/donate' }
    ],
    sidebar: {
      '/wiki/': getSidebar('')
    },
    socialLinks: [
      { icon: 'github', link: 'https://github.com/axperty/yakisugi' },
      { icon: 'discord', link: 'https://discord.gg/e2BQx4bbsU' },
      { icon: 'youtube', link: 'https://www.youtube.com/@axperty' }
    ],
    footer: {
      message: '<a href="/privacy">Privacy Policy</a><br/> Yakisugi is licensed under the <a href="https://github.com/axperty/yakisugi/blob/26.1-neoforge/LICENSE.md" target="_blank" rel="noopener">Yakisugi Mod License</a>.<br/> Not an official Minecraft product. Not approved by or associated with Mojang or Microsoft.<br/> All other trademarks and logos are property of their respective owners.',
      copyright: 'Copyright © 2026 Axperty. Website source code is under the MIT License.'
    }
  }
})
