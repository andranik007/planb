package com.example.compl.Service;

import com.example.compl.Models.StatmentModel;
import com.example.compl.Models.StatmentStatus;
import com.example.compl.Models.UserModel;
import com.example.compl.repo.StatmentRepo;
import com.example.compl.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class StatmentService {
    private final UserRepo userRepo;
    private final StatmentRepo statmentRepo;

    public void store(StatmentModel data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserModel user = userRepo.findByUsername(username);
        StatmentModel statmentModel = new StatmentModel();
        statmentModel.setUser(user);
        statmentModel.setName(data.getName());
        statmentModel.setStatmentStatus(StatmentStatus.НОВЫЙ);
        statmentRepo.save(statmentModel);
    }

    public void edit(StatmentModel data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        UserModel user = userRepo.findByUsername(username);
        StatmentModel statmentModel = statmentRepo.findById(data.getId()).orElse(null);
        statmentModel.setName(data.getName());
        statmentModel.setUser(user);
        statmentModel.setStatmentStatus(data.getStatmentStatus());
        statmentRepo.save(statmentModel);
    }

    public void adminEdit(StatmentModel data) {
        StatmentModel statmentModel = statmentRepo.findById(data.getId()).orElse(null);
        statmentModel.setName(data.getName());
        statmentModel.setStatmentStatus(data.getStatmentStatus());
        statmentRepo.save(statmentModel);
    }

    public List<StatmentModel> findByUser(UserModel user) {
        return statmentRepo.findByUser(user);
    }

    public List<StatmentStatus> getAllStatus() {
        return Arrays.asList(StatmentStatus.values());
    }
}
