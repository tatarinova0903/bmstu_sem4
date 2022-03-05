#include "memory.h"

return_code allocate_arr(point_t *&arr, size_t n)
{
    point_t *buf = new struct point[n];
    if (!buf)
        return ERR_MEMORY;
    arr = buf;
    return OK;
}
