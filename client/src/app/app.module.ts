import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { HttpClientModule, provideHttpClient, withFetch } from "@angular/common/http"; // Removed HttpClientModule
import { AppComponent } from "./app.component";
import { CommonModule } from "@angular/common";

@NgModule({
    declarations: [],
    imports: [BrowserModule, CommonModule, HttpClientModule, AppComponent],
    providers: [provideHttpClient()], // Correct way to provide HttpClient
})
export class AppModule {}
