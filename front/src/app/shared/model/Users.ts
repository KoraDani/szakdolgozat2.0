import { v4 as uuid } from 'uuid';

export interface Users {
  userId: number | null;
  username: string;
  email: string;
  password: string;
  imageUrl: string;
  deviceList: string[] | null;
  role: string;
}
