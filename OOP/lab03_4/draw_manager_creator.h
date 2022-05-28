#ifndef DRAW_MANAGER_CREATOR_H
#define DRAW_MANAGER_CREATOR_H

#include <memory>
#include "draw_manager.h"

class draw_manager_creator
{
public:
    std::shared_ptr<draw_manager> create_man();

private:
    std::shared_ptr<draw_manager> manager_;
    void create_instance();
};

#endif // DRAW_MANAGER_CREATOR_H
