import { Component, OnInit } from '@angular/core';
import { FormErrors, User, UserDto, UserRole } from "../../../shared/models.interfaces";
import { FormBuilder, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { UserService } from "../../../services/user.service";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  title = 'Данные нового пользователя';
  user: User | null = null;
  userDto: UserDto = {
    userName: '', email: '', password: '', role: UserRole.ROLE_MANAGER
  };
  editMode = false;
  editForm = this.fb.group({
    userName: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    role: [UserRole.ROLE_MANAGER, Validators.required]
  });
  formErrors: FormErrors = {};

  UserRole = UserRole;

  private $userSub = new Subscription();

  constructor(
      private fb: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      public userService: UserService,
  ) { }

  ngOnInit(): void {
    this.$userSub = this.route.params.subscribe(params => {
      this.editMode = params['id'] && params['id'] !== null;
      if (this.editMode) {
        this.title = 'Редактировать данные пользователя';
        const id = params['id'];
        this.userService.load(id).subscribe(user => {
          this.user = user;
          this.prefillFormForEdit(user);
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.$userSub.unsubscribe();
  }

  private prefillFormForEdit(user: User) {
    this.userDto.id = user.id;
    this.editForm.patchValue({
      userName: user.userName,
      email: user.email,
      role: user.role,
    });
  }

  onSaveClick() {
    this.userDto.userName = this.editForm.value.userName;
    this.userDto.email = this.editForm.value.email;
    this.userDto.role = this.editForm.value.role;
    this.userDto.password = this.editForm.value.password;

    this.checkAndShowFormErrors();
    if (this.editForm.valid) {
      this.saveOrUpdate(this.userDto);
      this.router.navigate(['/main/users']);
    }
  }

  onDiscardClick() {
    if (this.user) {
      this.prefillFormForEdit(this.user);
    } else {
      this.editForm.controls['userName'].reset();
      this.editForm.controls['email'].reset();
      this.editForm.controls['role'].reset();
      this.editForm.controls['password'].reset();
    }
    this.formErrors = {};
  }

  private checkAndShowFormErrors() {
    console.log(this.editForm);
    for (const controlName in this.editForm.controls) {
      const formControl = this.editForm.controls[controlName];
      if (formControl.errors) {
        this.formErrors[controlName] = 'Проверьте правильность заполнения!'
      }
    }
  }

  private saveOrUpdate(dto: UserDto) {
    if (this.editMode) {
      this.userService.update(dto);
    } else {
      this.userService.save(dto);
    }
  }

}
