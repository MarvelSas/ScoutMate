import { Pipe, PipeTransform } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';



@Pipe({
  name: 'secure'
})
export class SecurePipe implements PipeTransform {
  constructor(private http: HttpClient, private sanitizer: DomSanitizer) { }

  async transform(src: string): Promise<string> {
    if (src.indexOf(';base64,') > -1) {
      return src;
    }
    const imageBlob  = await this.http.get(src, { responseType: 'blob' }).toPromise() as Blob;
    const reader = new FileReader();
    return new Promise((resolve, reject) => {
      reader.onloadend = () => resolve(reader.result as string);
     reader.readAsDataURL(imageBlob);
    });
  }

}
