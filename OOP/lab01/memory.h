#ifndef MEMORY_H
#define MEMORY_H

#include <stdlib.h>

#include "points.h"
#include "return_code.h"

return_code allocate_arr(point_t *&arr, size_t n);

#endif // MEMORY_H
