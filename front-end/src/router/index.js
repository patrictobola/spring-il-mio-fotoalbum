import { createRouter, createWebHistory } from 'vue-router';
import index from '@/components/index.vue';
import PhotoDetail from '@/components/PhotoDetail.vue'

const routes = [
  { path: '/', name: 'index', component: index },
  { path: '/photo/:id', name: 'photo-detail', component: PhotoDetail },

];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
