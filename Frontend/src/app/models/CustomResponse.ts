export class CustomResponse {
  constructor(
    public timeStamp?: Date,
    public statusCode?: number,
    public status?: number,
    public reason?: String,
    public message?: String,
    public developerMessage?: String,
    public data?: any) {}
}
