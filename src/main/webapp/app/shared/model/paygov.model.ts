export interface IPaygov {
  id?: number;
  cik?: string;
  ccc?: number;
  paymentAmount?: number;
  name?: string;
  email?: string;
  phone?: string;
}

export const defaultValue: Readonly<IPaygov> = {};
