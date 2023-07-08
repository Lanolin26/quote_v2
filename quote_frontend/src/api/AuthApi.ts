import { AxiosInstance } from "axios";

export class AuthApi {
  axiosInstance: AxiosInstance;

  public constructor(axiosInstance: AxiosInstance) {
    this.axiosInstance = axiosInstance;
  }
}
