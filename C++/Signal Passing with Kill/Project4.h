#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <signal.h>
#include <errno.h>
#include <sys/types.h>

#define MAX_TIME 60

#ifndef SIG_H

#define SIG_H
#define MYSIGNAL SIGUSR1

#endif
