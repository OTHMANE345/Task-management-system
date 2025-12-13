import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CustomInputComponent } from "./components/custom-input/custom-input.component";
import { CustomTitleComponent } from "./components/custom-title/custom-title.component";
import { CustomItemComponent } from "./components/custom-item/custom-item.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CustomInputComponent, CustomTitleComponent, CustomItemComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
