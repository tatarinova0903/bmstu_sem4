#include "input.h"
#include "matrix.h"
#include "points.h"
#include "figure.h"

#include <iostream>

void rewind_file(FILE *stream)
{
    rewind(stream);
}

return_code read_line_point(FILE *f, point_t &p)
{
   int n;
   double x,y,z;
   if (fscanf(f, "%d %lf %lf %lf",&n, &x, &y, &z) == 4)
   {
       set_point(p,x,y,z,n);
       return OK;
   }
   return ERR_INPUT;
}

return_code read_line_mt_el(FILE *f, int &mi, int &mj)
{
   if (fscanf(f, "%d->%d", &mi, &mj) == 2)
       return OK;
   return ERR_INPUT;
}

int count_points(FILE *f)
{
    struct point p;
    size_t num = 0;
    while (read_line_point(f, p) == OK)
    {
        num++;
    }
    rewind_file(f);
    return num;
}

return_code read_from_file(figure &fig, FILE *f)
{
    return_code rc = OK;
    if (!f)
    {
        rc = ERR_EMPTY;
    }
    else
    {
        free_fig(fig);
        fig = init_fig();

        fig.n = count_points(f);
        rc = alloc_fig(fig, fig.n);
        if (rc == OK)
        {
            rc = create_fig(fig, fig.n, f);
            if (rc != OK)
            {
                free_fig(fig);
            }
        }
    }
    return rc;
}
