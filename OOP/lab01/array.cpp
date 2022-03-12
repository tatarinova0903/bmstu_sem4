#include "array.h"

return_code allocate_arr(point_t *&arr, size_t n)
{
    return_code rc = OK;
    point_t *buf = new point_t[n];
    if (!buf)
    {
        rc = ERR_MEMORY;
    }
    else
    {
        arr = buf;
    }
    return rc;
}
