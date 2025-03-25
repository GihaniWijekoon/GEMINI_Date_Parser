import { Component } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [CommonModule, HttpClientModule, FormsModule] 
})
export class AppComponent {
  requestText = '';
  response: any;
  responseDate: any;
  history: any[] = [];

  sentText: string ='';

  constructor(private http: HttpClient) {}

  processText() {
    this.http.post<any>('http://localhost:8080/api/requests', { requestText: this.requestText }) 
      .subscribe(
        (data) => {
          console.log("sent input:",this.requestText);
          console.log("response is..", data);
          this.response = data;
          this.responseDate = data.parsedDate;
          this.sentText = data.requestText;
          this.history.push({ request: this.requestText, response: data });
          this.requestText = ''; 
        },
        (error) => {
          console.error('Error:', error);
        }
      );
  }

  fetchHistory() {
    this.http.get<any[]>('http://localhost:8080/api/requests') // Adjust endpoint as needed
      .subscribe(
        (data) => {
          console.log("Fetched history:", data);
          this.history = data;
        },
        (error) => {
          console.error('Error fetching history:', error);
        }
      );
  }

  ngOnInit() {
    this.fetchHistory();
  }
}
