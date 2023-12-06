import {Component} from '@angular/core';
import {SpecimenService} from "../specimen/service/specimen.service";
import {DoctorService} from "../doctor/service/doctor.service";
import {ReferringCenterService} from "../referring-center/service/referring-center.service";
import {forkJoin} from "rxjs";

@Component({
  selector: 'jhi-dashboard',
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {
  public specimenStatusDoughnutChartLabels = ['RECEIVED', 'GROSSING', 'PROCESSING', 'DIAGNOSING', 'TYPING', 'REVISION', 'READY'];
  public specimenStatusDoughnutChartData: any;
  public specimenStatusData: Array<any> = [];

  public pieChartLabels = ['شهر ٤', 'شهر ٣', 'شهر٢', 'شهر ١'];
  public pieChartData = [43, 54, 65, 33];

  specimenCount: any;
  doctorCount: any;
  referringCenterCount: any;

  constructor(
    private specimenService: SpecimenService,
    private doctorService: DoctorService,
    private referringCenterService: ReferringCenterService
  ) {
  }

  ngOnInit(): void {
    this.specimenService.count().subscribe((res: any) => {
      this.specimenCount = res.body;
    });
    this.doctorService.count().subscribe((res: any) => {
      this.doctorCount = res.body;
    });
    this.referringCenterService.count().subscribe((res: any) => {
      this.referringCenterCount = res.body;
    });

    forkJoin([
      this.specimenService.count({'specimenStatus.equals': 'RECEIVED'}),
      this.specimenService.count({'specimenStatus.equals': 'GROSSING'}),
      this.specimenService.count({'specimenStatus.equals': 'PROCESSING'}),

      this.specimenService.count({'specimenStatus.equals': 'DIAGNOSING'}),
      this.specimenService.count({'specimenStatus.equals': 'TYPING'}),
      this.specimenService.count({'specimenStatus.equals': 'REVISION'}),
      this.specimenService.count({'specimenStatus.equals': 'READY'}),
    ]).subscribe(data => {
      this.specimenStatusData.push(data[0].body);
      this.specimenStatusData.push(data[1].body);
      this.specimenStatusData.push(data[2].body);
      this.specimenStatusData.push(data[3].body);
      this.specimenStatusData.push(data[4].body);
      this.specimenStatusData.push(data[5].body);
      this.specimenStatusData.push(data[6].body);
      this.specimenStatusDoughnutChartData = this.specimenStatusData;
    });
  }
}
