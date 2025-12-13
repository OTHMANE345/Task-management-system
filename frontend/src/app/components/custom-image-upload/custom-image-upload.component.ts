import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-custom-image-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './custom-image-upload.component.html',
  styleUrl: './custom-image-upload.component.css'
})
export class CustomImageUploadComponent {
 image: any = null;
 selectedFile: File | null = null;
 fileSize : any;
 onFileChange(event: any){
  const file = event.target.files[0] as File | null;
  if(file && file.type.startsWith('image/')){
    this.selectedFile = file;
    this.fileSize = (Math.round((file.size /1024)));
    const reader = new FileReader();
    reader.onload = (e: any) => {
      this.image = e.target?.result as string;
    }
    reader.readAsDataURL(file);
  }
 }
}
