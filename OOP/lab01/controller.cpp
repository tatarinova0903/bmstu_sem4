#include "controller.h"
#include "rc.h"
#include "functions.h"
#include <iostream>


ReturnCode controller(myscene_t scene, ActionType action, action_t act)
{
    static figure_t fig = init_fig();
    ReturnCode rc = OK;
    switch (action) {
        case DOWNLOAD:
        rc = download_model(fig, act);
        break;
    case MOVE:
        rc = move_fig(fig,act);
        break;
    case ROTATE:
        rc = rotation_fig(fig,act);
        break;
    case SCALE:
        rc = scale_fig(fig,act);
        break;
    case DELETE:
        rc = clear_fig(fig);
        break;
    case DRAW:
        rc = draw_fig(fig, scene);
        break;
    default:
        rc = ERR_PARAMETR;
    }
    return rc;
}
