import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const roleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const userRole = localStorage.getItem('role');
  const requiredRoles = route.data['roles'] as string[];

  if(!userRole){
    router.navigate(['/login']);
    return false;
  }

  if(requiredRoles && !requiredRoles.includes(userRole)){
    console.log('go to not found page')
    return false;
  }
  return true;
};
