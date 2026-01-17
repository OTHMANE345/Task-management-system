import { SlicePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-custom-item',
  standalone: true,
  imports: [SlicePipe],
  templateUrl: './custom-item.component.html',
  styleUrl: './custom-item.component.css'
})
export class CustomItemComponent {
@Input() task : any;

constructor(private router: Router){}

getImageUrl(image: any){
  URL.createObjectURL(image);
}
showDetail(){
  this.router.navigate(['/layout/task', this.task.id]);
}

}