export interface Credentials {
    username: string
    jwt: string 
    // Supicku materinu sto sam ovde stavio JWT i trazio zasto mi ne prebacuje s beka dobro token na front dobrih pola sata..
    // Barem sam prosao 3 puta kroz interceptor, generisanje JWT na beku i djubradi oko rola...
    userType: string
}