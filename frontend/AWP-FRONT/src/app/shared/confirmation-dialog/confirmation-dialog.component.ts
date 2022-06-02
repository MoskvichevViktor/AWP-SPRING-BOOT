import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  templateUrl: `./confirmation-dialog.component.html`,
  styleUrls: [ `./confirmation-dialog.component.scss` ],
})
export class ConfirmationDialogComponent {

  constructor(
      public dialogRef: MatDialogRef<ConfirmationDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: ConfirmationDialogConfig) {}

  public onConfirmClick() {
    this.dialogRef.close( true );
  }

  public onCancelClick() {
    this.dialogRef.close( false );
  }

}

export interface ConfirmationDialogConfig {
  title: string;
  message: string;
  confirmButtonTitle: string;
  cancelButtonTitle: string;
}
