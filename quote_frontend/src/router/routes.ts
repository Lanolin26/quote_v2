import { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {//2b986d4d-4ca1-47c3-819b-2d4615c74c2b
    path: '/quote/:quoteId([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})',
    name: 'OneQuotePage',
    component: () => import('pages/OneQuotePage.vue'),
    props: true
  },

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
      { path: '/', name: '', component: () => import('pages/QuoteListPage.vue') },
      { path: 'view', name: 'QuoteListPage', component: () => import('pages/QuoteListPage.vue') },
      { path: 'create', name: 'CreateQuotePage', component: () => import('pages/CreateQuotePage.vue') },
    ],
  },

  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
];

export default routes;
