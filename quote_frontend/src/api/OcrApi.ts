import { AxiosInstance } from "axios";

export class OcrApi {
  axiosInstance: AxiosInstance;

  public constructor(axiosInstance: AxiosInstance) {
    this.axiosInstance = axiosInstance;
  }
}
