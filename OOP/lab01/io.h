#ifndef IO_H
#define IO_H

#include <stdlib.h>
#include <stdio.h>

#include "points.h"
#include "rc.h"

void free_fig(struct figure &fig);
return_code read_from_file(struct figure &fig, FILE *f);

#endif // IO_H

