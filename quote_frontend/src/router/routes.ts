import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    path: '/admin',
    component: () => import('layouts/AdminLayout.vue'),
    children: [
      { path: '', name: 'AdminIndexPage', component: () => import('pages/IndexPage.vue') },
      { path: 'source-type', name: 'ManageSourceTypePage', component: () => import('pages/admin/ManageSourceTypePage.vue') },
      { path: 'source', name: 'ManageSourcePage', component: () => import('pages/admin/ManageSourcePage.vue') },
      { path: 'quote', name: 'ManageQuotePage', component: () => import('pages/admin/ManageQuotePage.vue') },
      { path: 'users', name: 'ManageUserPage', component: () => import('pages/admin/ManageUserPage.vue') },
    ],
  },

  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', name: 'QuoteListPage', component: () => import('pages/QuoteListPage.vue') },
    ],
  },

  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
