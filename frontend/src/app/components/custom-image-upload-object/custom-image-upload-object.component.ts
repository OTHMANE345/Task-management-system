import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-custom-image-upload-object',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './custom-image-upload-object.component.html',
  styleUrl: './custom-image-upload-object.component.css'
})
export class CustomImageUploadObjectComponent {
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
      console.log("type blob", this.selectedFile)
    }
     reader.readAsDataURL(file);
  }
 }
}
