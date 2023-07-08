import { boot } from 'quasar/wrappers';
import axios, { AxiosInstance } from 'axios';
import { AuthApi } from "src/api/AuthApi";
import { QuoteApi } from "src/api/QuoteApi";
import { SourceApi } from "src/api/SourceApi";
import { SourceTypeApi } from "src/api/SourceTypeApi";
import { UserApi } from "src/api/UserApi";
import { Apis } from "src/api/AbstractApi";

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $axios: AxiosInstance;
    $api: Apis;
  }
}

// Be careful when using SSR for cross-request state pollution
// due to creating a Singleton instance here;
// If any client changes this (global) instance, it might be a
// good idea to move this instance creation inside of the
// "export default () => {}" function below (which runs individually
// for each client)
const apiAxios = axios.create({ baseURL: '/' });

const api: Apis = {
  auth: new AuthApi(apiAxios),
  quote: new QuoteApi(apiAxios),
  source: new SourceApi(apiAxios),
  sourceType: new SourceTypeApi(apiAxios),
  user: new UserApi(apiAxios)
};

export default boot(({ app }) => {
  // for use inside Vue files (Options API) through this.$axios and this.$api

  app.config.globalProperties.$axios = axios;
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = api;
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API
});

export { api };
