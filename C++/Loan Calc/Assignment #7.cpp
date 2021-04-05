#include <stdio.h>
#include <math.h>
// assignment #7


float loancalc(struct data d,struct loan l);
float advancecalc(struct data d,struct advance a);
float difcalc(struct advance a, struct loan l);

struct data{
	float remain1;
	float remain2;
	float total=1000;
	float rate=8;
	int years=1;
	int payments=12;
};

struct advance{
	float atotal;
};

struct loan{
	float ltotal;
};

int main()
{

struct data d;
struct loan l;
struct advance a;
float perdif;
	d.remain1 = loancalc(d,l);
	printf("%f\n",d.remain1);
	d.remain2 = advancecalc(d,a);
	printf("%f\n",d.remain2);
	perdif= difcalc(a,l);
	printf("%f",perdif);
	
	return 0;
}

float loancalc(struct data d,struct loan l)
{
	float top = (d.rate/100)*d.total/d.payments;
	float bottom = 1-pow((((d.rate/100)/d.payments)+1),((-1)*d.payments*d.years));
	l.ltotal =(top/bottom);
	printf("%f\n",l.ltotal);
	return l.ltotal; 
}

float advancecalc(struct data d,struct advance a)
{
	float top2 = (d.rate/100)/d.payments;
	float bot2 = pow((1+((d.rate/100)/d.payments)),(d.payments*d.years))-1;
	float frac = (top2/bot2);
	a.atotal = d.total*frac;
	
	return a.atotal;
}

float difcalc(struct advance a, struct loan l)
{
	float dif;
	float percent;
	dif = a.atotal - l.ltotal;
	percent = dif/a.atotal;
	printf("\n\n\n%f\n%f",a.atotal,l.ltotal);
	printf(" PERCENT: %f",percent);
	return percent;
}
