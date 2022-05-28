#include "reform_manager_creator.h"

void reform_manager_creator::create_instance()
{
    static std::shared_ptr<reform_manager> m(new reform_manager());

    manager_ = m;
}

std::shared_ptr<reform_manager> reform_manager_creator::create_man()
{
    if (manager_ == nullptr)
    {
        create_instance();
    }

    return manager_;
}
