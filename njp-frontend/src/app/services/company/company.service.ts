import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Company } from 'src/app/models/company';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private httpClient: HttpClient) { }

  private companyURL:string = 'http://localhost:8081/company'


 

  createCompany(company:Company):Observable<Company> {
    return this.httpClient.post<Company>(this.companyURL+"/save", company);
  }

  getCompanies():Observable<Company[]>{
    return this.httpClient.get<Company[]>(this.companyURL+"/all");
  }
  
  getCompanyById(companyId:number):Observable<Company>{
    return this.httpClient.get<Company>(this.companyURL+"/get/"+companyId);
  }
}
