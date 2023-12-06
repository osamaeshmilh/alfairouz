import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from "@angular/core";
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {formatDate} from "@angular/common";


@Injectable({providedIn: 'root'})
export class ReportsService {

  format = 'yyyy-MM-dd';
  locale = 'en-US';

  constructor(protected http: HttpClient) {
  }

  // Function to download the file with token header
  downloadFileWithToken(from: any, to: any): void {
    const formattedFromDate = formatDate(from, this.format, this.locale);
    const formattedToDate = formatDate(to, this.format, this.locale);

    // Make a GET request to the API to get the file data
    this.http.get(`/api/public/specimen/xlsx/criteria/?receivingDate.greaterThanOrEqual=${formattedFromDate}&receivingDate.lessThanOrEqual=${formattedToDate}`, {
      responseType: 'arraybuffer' as 'json' // Set the response type to arraybuffer
    })
      .subscribe((data: any) => {
        // Create a Blob from the file data
        const blob = new Blob([data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'});

        // Create a Blob URL
        const blobUrl = URL.createObjectURL(blob);

        // Open a new window with the Blob URL
        window.open(blobUrl, '_blank');
      });
  }
}
