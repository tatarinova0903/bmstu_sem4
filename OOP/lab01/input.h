#ifndef INPUT_H
#define INPUT_H

#include <stdlib.h>
#include <stdio.h>

#include "points.h"
#include "return_code.h"

return_code read_from_file(struct figure &fig, FILE *f);
return_code read_line_point(FILE *f, point_t &p);
return_code read_line_mt_el(FILE *f, int &mi, int &mj);

#endif // INPUT_H

