import { createRouter, createWebHashHistory } from 'vue-router';
import HomeView from '../views/Home.vue';
import FollowingView from '../views/Following.vue';
import ExploreView from '../views/Explore.vue';
import LiveView from '../views/Live.vue';
import UploadView from '../views/Upload.vue';
import MessagesView from '../views/Messages.vue';
import UserView from '../views/User.vue';
import SearchView from '../views/Search.vue';
import ErrorView from '../views/Error.vue';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/following',
      name: 'following',
      component: FollowingView
    },
    {
      path: '/explore',
      name: 'explore',
      component: ExploreView
    },
    {
      path: '/live',
      name: 'live',
      component: LiveView
    },
    {
      path: '/upload',
      name: 'upload',
      component: UploadView
    },
    {
      path: '/messages',
      name: 'messages',
      component: MessagesView
    },
    {
      path: '/user/:id',
      name: 'user',
      props: true,
      component: UserView
    },
    {
      path: '/search/:key',
      name: 'search',
      props: true,
      component: SearchView
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'error',
      component: ErrorView
    }
  ]
});

export default router;
