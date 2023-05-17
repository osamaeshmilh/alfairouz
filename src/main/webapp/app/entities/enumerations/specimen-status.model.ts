export enum SpecimenStatus {
  RECEIVED = 'RECEIVED',
  GROSSING = 'GROSSING',
  PROCESSING = 'PROCESSING',

  // EMBEDDING = 'EMBEDDING',
  //
  // CUTTING = 'CUTTING',
  //
  // STAINING = 'STAINING',

  DIAGNOSING = 'DIAGNOSING',
  TYPING = 'TYPING',
  REVISION = 'REVISION',
  READY = 'READY',
}

const statusOrder: SpecimenStatus[] = [
  SpecimenStatus.RECEIVED,
  SpecimenStatus.GROSSING,
  SpecimenStatus.PROCESSING,
  SpecimenStatus.DIAGNOSING,
  SpecimenStatus.TYPING,
  SpecimenStatus.REVISION,
  SpecimenStatus.READY,
];
