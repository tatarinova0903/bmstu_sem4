#include "draw_manager_creator.h"

void draw_manager_creator::create_instance()
{
    static std::shared_ptr<draw_manager> manager(new draw_manager());

    manager_ = manager;
}


std::shared_ptr<draw_manager> draw_manager_creator::create_man()
{
    if (manager_ == nullptr)
    {
        create_instance();
    }

    return manager_;
}

