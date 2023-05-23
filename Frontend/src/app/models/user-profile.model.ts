export interface UserProfile {
  timeStamp: string
  statusCode: number
  status: string
  message: string
  data: Data
}

export interface Data {
  userProfilDetails: UserProfilDetails
}

export interface UserProfilDetails {
  email: string
  name: string
  surname: string
  birthday: string
  nickName: string
  roles: any[]
  meritBadges: any[]
  scoutRank: any
  scoutInstructorRank: any
  photo: any
}
