#include "controller.h"
#include "functions.h"

return_code controller(myscene_t scene, data_type action, data_t act)
{
    static figure_t fig = init_fig();
    return_code rc = OK;
    switch (action) {
    case DOWNLOAD:
        rc = download_model(fig, act);
        break;
    case MOVE:
        rc = move_fig(fig, act);
        break;
    case ROTATE:
        rc = rotation_fig(fig, act);
        break;
    case SCALE:
        rc = scale_fig(fig, act);
        break;
    case DELETE:
        clear_fig(fig);
        break;
    case DRAW:
        draw_fig(fig, scene);
        break;
    default:
        rc = ERR_PARAMETR;
    }
    return rc;
}
