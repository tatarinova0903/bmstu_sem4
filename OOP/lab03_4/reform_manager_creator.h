#ifndef REFORM_MANAGER_CREATOR_H
#define REFORM_MANAGER_CREATOR_H

#include "reform_manager.h"

class reform_manager_creator
{
public:
    std::shared_ptr<reform_manager> create_man();

private:

    std::shared_ptr<reform_manager> manager_;

    void create_instance();


};

#endif // REFORM_MANAGER_CREATOR_H
