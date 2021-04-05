
// if statements for requested base
#ifdef BINARY 
#define BASE 2
#endif

#ifdef HEXADECIMAL
#define	BASE 16
#endif

#ifdef OCTAL
#define	BASE 8
#endif


//exchange odd bytes
#define XODD(num) num; do {\
int a = (0x0000FF00 & num);\
int b = (0xFF000000 & num);\
a = (a << 16) & 0xFF000000;\
b = (b >> 16) & 0x0000FF00;\
num = num & 0x00FF00FF;\
num = num ^ a;\
num = num ^ b;\
}while(0)


//exchange even bytes
#define XEVEN(num) num; do {\
int a = (0x000000FF & num);\
int b = (0x00FF0000 & num);\
a = (a << 16) & 0x00FF0000;\
b = (b >> 16) & 0x000000FF;\
num = num & 0xFF00FF00;\
num = num ^ a;\
num = num ^ b;\
}while(0)
