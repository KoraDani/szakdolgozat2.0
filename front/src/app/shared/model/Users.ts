import { v4 as uuid } from 'uuid';

export interface Users {
  id: any| undefined;
  username: string;
  email: string;
  password: string;
  imageUrl: string;

}
