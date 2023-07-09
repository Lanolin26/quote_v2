import { AxiosInstance } from 'axios';
import { OcrLanguage, StatusMessage } from 'components/models';

export class OcrApi {
  axiosInstance: AxiosInstance;

  public constructor(axiosInstance: AxiosInstance) {
    this.axiosInstance = axiosInstance;
  }

  sendToRecognize(file?: File, lang?: OcrLanguage): Promise<string> {
    if (file) {
      const formData = new FormData();
      formData.append('image', file);
      formData.append('lang', lang ? lang : 'rus');
      return this.axiosInstance
        .post('/api/ocr/tesseract', formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
        })
        .then((data) => data.data as StatusMessage)
        .then((value) => (value.message ? value.message : ''));
    }
    return Promise.resolve('');
  }
}
