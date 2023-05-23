export interface CreatedAttempt {
  timeStamp: string
  statusCode: number
  status: string
  message: string
  data: Data
}

export interface Data {
  getAllCreatedAttempt: GetAllCreatedAttempt[]
}

export interface GetAllCreatedAttempt {
  id: number
  name: string
  attemptTasks: AttemptTask[]
  creatorId: CreatorId
  applicantId: ApplicantId
  organization: Organization
  scoutRankId?: ScoutRankId
  scoutInstructorRankId?: ScoutInstructorRankId
  meritBadgeId?: MeritBadgeId
  STATUS: string
  TYPE: string
  archived: boolean
}

export interface AttemptTask {
  id: number
  description: string
}

export interface CreatorId {
  email: string
  name: string
  surname: string
}

export interface ApplicantId {
  email: string
  name: string
  surname: string
}

export interface Organization {
  id: number
  name: string
  organizationType: string
}

export interface ScoutRankId {
  id: number
  name: string
  imageURL: string
  level: number
  statutePDFURL: string
}

export interface ScoutInstructorRankId {
  id: number
  name: string
  imageURL: string
  level: number
  color: string
  statutePDFURL: string
  shortcut: string
}

export interface MeritBadgeId {
  id: number
  name: string
  imageURL: string
}
