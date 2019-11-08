/**
 * 
 */
package com.frac.FracAdvanced.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frac.FracAdvanced.model.ProjectDetails;
import com.frac.FracAdvanced.model.ReservoirFluidModel;
import com.frac.FracAdvanced.repository.ProjectDetailRepo;
import com.frac.FracAdvanced.repository.ReservoirFluidRepo;

/**
 * @author ShubhamGaur
 *
 */
@Service
public class ReservoirFluidService1 {

	@Autowired
	ReservoirFluidRepo fluidRepo;
	@Autowired
	ProjectDetailRepo detailRepo;

	public boolean showList(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> fluidModels = fluidRepo.findBydetails(details);
		boolean status = true;

		return false;
	}

	public void saveResFluidData(Integer pid, List<String> input) {
		List<ReservoirFluidModel> list = savedataList(pid);
		List<ReservoirFluidModel> savelist = savedataList(pid);
		switch (number(pid)) {
		case 1:
			for (int i = 0; i < list.size(); i++) {
				ReservoirFluidModel fluidModel = list.get(i);
				fluidModel.setValue(input.get(i));
				savelist.add(fluidModel);
			}
			break;
		case 2:
			int x = 0, y = 1;
			for (int i = 0; i < list.size(); i++) {
				ReservoirFluidModel fluidModel = list.get(i);
				if (list.get(0).getFluidtype().equalsIgnoreCase("oil")) {
					if (i < 5 && x < 9) {
						fluidModel.setValue(input.get(x));
						savelist.add(fluidModel);
						x += 2;
					} else if (i >= 5 && y < 12) {
						if (i >= 9) {
							fluidModel.setValue(input.get(y));
							savelist.add(fluidModel);
							y += 1;
						} else {
							fluidModel.setValue(input.get(y));
							savelist.add(fluidModel);
							y += 2;
						}
					}
				} else {
					if (i < 7 && x < 13) {
						fluidModel.setValue(input.get(x));
						savelist.add(fluidModel);
						x += 2;
					} else if (i >= 5 && y < 14) {
						fluidModel.setValue(input.get(y));
						savelist.add(fluidModel);
						y += 2;
					}

				}
			}
			break;
		case 3:
			int k = 0, l = 1, m = 2;
			for (int i = 0; i < list.size(); i++) {
				ReservoirFluidModel fluidModel = list.get(i);
				if (i < 5 && k < 15) {
					fluidModel.setValue(input.get(k));
					savelist.add(fluidModel);
					k += 3;
				} else if (i >= 5 && i < 12 && l < 17) {
					if (i == 10 || i == 11) {
						fluidModel.setValue(input.get(l));
						savelist.add(fluidModel);
						l += 1;
					} else if (i == 9) {
						fluidModel.setValue(input.get(l));
						savelist.add(fluidModel);
						l += 2;
					} else {
						fluidModel.setValue(input.get(l));
						savelist.add(fluidModel);
						l += 3;
					}
				} else if (i >= 12 && m < 19) {
					if (i == 17 || i == 18) {
						fluidModel.setValue(input.get(m));
						savelist.add(fluidModel);
						m += 1;
					} else {
						fluidModel.setValue(input.get(m));
						savelist.add(fluidModel);
						m += 3;
					}
				}
			}
			break;
		}
		fluidRepo.saveAll(savelist);
	}

	public List<ReservoirFluidModel> savedataList(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> fluidModels = fluidRepo.findBydetails(details);
		List<ReservoirFluidModel> list = new ArrayList<ReservoirFluidModel>();
		for (int i = 0; i < fluidModels.size(); i++) {
			if (!fluidModels.get(i).getFluidtype().startsWith("N/A")) {
				list.add(fluidModels.get(i));
			}
		}
		return list;
	}

	public Integer number(Integer pid) {
		Integer no = 0;
		if (savedataList(pid).size() == 5 || savedataList(pid).size() == 7) {
			no = 1;
		} else if (savedataList(pid).size() == 12 || savedataList(pid).size() == 14) {
			no = 2;
		} else if (savedataList(pid).size() == 19) {
			no = 3;
		}
		return no;
	}

	public Map<String, List<ReservoirFluidModel>> showSavedData(Integer pid, String type) {
		String[] fluidtypes = type.split("-");
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		Map<String, List<ReservoirFluidModel>> map = new LinkedHashMap<String, List<ReservoirFluidModel>>();
		for (int i = 0; i < fluidtypes.length; i++) {
			List<ReservoirFluidModel> fluidModels = fluidRepo.findByFluidtypeAndDetails(fluidtypes[i], details);
			map.put(fluidtypes[i], fluidModels);
		}
		return map;
	}

	public String getfluidtype(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> fluidModels = fluidRepo.findBydetails(details);
		List<String> list = new ArrayList<String>();
		String type = "";
		for (int i = 0; i < fluidModels.size(); i++) {
			if (!fluidModels.get(i).getFluidtype().startsWith("N/A")) {
				list.add(fluidModels.get(i).getFluidtype());
			}
		}
		LinkedHashSet<String> set = new LinkedHashSet<String>(list);
		ArrayList<String> list2 = new ArrayList<String>(set);
		for (int i = 0; i < list2.size(); i++) {
			type += list2.get(i);
			if (i < list2.size() - 1) {
				type += "-";
			}
		}
		return type;
	}

	public Map<String, List<String>> saveReservoirFluidtype(Integer pid, String fluidtype) {
		String[] fluidtypes = fluidtype.split("-");
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> fluidModels = fluidRepo.findBydetails(details);
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		List<String> list3 = new ArrayList<String>();
		switch (fluidtype.toLowerCase()) {
		case "oil":
			for (int i = 0; i < fluidModels.size(); i++) {
				ReservoirFluidModel model = fluidModels.get(i);
				if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_oil")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("oil")) {
					model.setFluidtype("oil");
					list.add(fluidModels.get(i).getParam());
				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("gas")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("water")) {
					model.setFluidtype("N/A_" + fluidModels.get(i).getFluidtype());
				}
				fluidRepo.save(model);
			}
			map.put(fluidtypes[0], list);
			break;
		case "gas":
			for (int i = 0; i < fluidModels.size(); i++) {
				ReservoirFluidModel model = fluidModels.get(i);
				if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_gas")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("gas")) {
					model.setFluidtype("gas");
					list.add(fluidModels.get(i).getParam());
				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("oil")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("water")) {
					model.setFluidtype("N/A_" + fluidModels.get(i).getFluidtype());
				}
				fluidRepo.save(model);
			}
			map.put(fluidtypes[0], list);
			break;
		case "water":
			for (int i = 0; i < fluidModels.size(); i++) {
				ReservoirFluidModel model = fluidModels.get(i);
				if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_water")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("water")) {
					model.setFluidtype("water");
					list.add(fluidModels.get(i).getParam());
				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("gas")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("oil")) {
					model.setFluidtype("N/A_" + fluidModels.get(i).getFluidtype());
				}
				fluidRepo.save(model);
			}
			map.put(fluidtypes[0], list);
			break;
		case "oil-gas":
			for (int i = 0; i < fluidModels.size(); i++) {
				ReservoirFluidModel model = fluidModels.get(i);
				if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_oil")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("oil")) {
					model.setFluidtype("oil");
					list.add(fluidModels.get(i).getParam());

				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_gas")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("gas")) {
					model.setFluidtype("gas");
					list2.add(fluidModels.get(i).getParam());

				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("water")) {
					model.setFluidtype("N/A_" + fluidModels.get(i).getFluidtype());
				}
				fluidRepo.save(model);
			}
			map.put(fluidtypes[0], list);
			map.put(fluidtypes[1], list2);
			break;
		case "oil-water":
			for (int i = 0; i < fluidModels.size(); i++) {
				ReservoirFluidModel model = fluidModels.get(i);
				if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_oil")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("oil")) {
					model.setFluidtype("oil");
					list.add(fluidModels.get(i).getParam());

				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_water")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("water")) {
					model.setFluidtype("water");
					list2.add(fluidModels.get(i).getParam());

				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("gas")) {
					model.setFluidtype("N/A_" + fluidModels.get(i).getFluidtype());
				}
				fluidRepo.save(model);
			}
			map.put(fluidtypes[0], list);
			map.put(fluidtypes[1], list2);
			break;
		case "gas-water":
			for (int i = 0; i < fluidModels.size(); i++) {
				ReservoirFluidModel model = fluidModels.get(i);
				if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_gas")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("gas")) {
					model.setFluidtype("gas");
					list.add(fluidModels.get(i).getParam());

				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_water")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("water")) {
					model.setFluidtype("water");
					list2.add(fluidModels.get(i).getParam());

				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("oil")) {
					model.setFluidtype("N/A_" + fluidModels.get(i).getFluidtype());
				}
				fluidRepo.save(model);
			}
			map.put(fluidtypes[0], list);
			map.put(fluidtypes[1], list2);
			break;
		case "oil-gas-water":
			for (int i = 0; i < fluidModels.size(); i++) {
				ReservoirFluidModel model = fluidModels.get(i);
				if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_oil")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("oil")) {
					model.setFluidtype("oil");
					list.add(fluidModels.get(i).getParam());
				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_gas")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("gas")) {
					model.setFluidtype("gas");
					list2.add(fluidModels.get(i).getParam());
				} else if (fluidModels.get(i).getFluidtype().equalsIgnoreCase("n/a_water")
						|| fluidModels.get(i).getFluidtype().equalsIgnoreCase("water")) {
					model.setFluidtype("water");
					list3.add(fluidModels.get(i).getParam());
				}
				fluidRepo.save(model);
			}
			map.put(fluidtypes[0], list);
			map.put(fluidtypes[1], list2);
			map.put(fluidtypes[2], list3);
			break;
		default:
			System.out.println("DEFAULT.....");
			break;
		}
		return map;
	}

	public List<String> showImportList(Integer pid) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> fluidModels = fluidRepo.findBydetails(details);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < fluidModels.size(); i++) {
			ReservoirFluidModel model = fluidModels.get(i);
			if (!list.contains(model.getParam()) && !(model.getFluidtype().startsWith("N/A"))) {
				list.add(model.getParam());
			}
		}
		return list;
	}

	public void saveReservoirFluid(Integer pid, List<String> param, List<String> value, List<String> type) {
		if (type.get(0).equalsIgnoreCase("oil")) {
			saveOil(pid, param.subList(0, 5), value.subList(0, 5));
			if (type.size() > 1 && type.get(1).equalsIgnoreCase("gas")) {
				saveGas(pid, param.subList(5, 12), value.subList(5, 12));
				if (type.size() > 2 && type.get(2).equalsIgnoreCase("water")) {
					saveWater(pid, param.subList(12, 19), value.subList(12, 19));
				}
			} else if (type.size() > 1 && type.get(1).equalsIgnoreCase("water")) {
				saveWater(pid, param.subList(5, 12), value.subList(5, 12));
			}
		} else if (type.get(0).equalsIgnoreCase("gas")) {
			saveGas(pid, param.subList(0, 7), value.subList(0, 7));
			if (type.size() > 1 && type.get(1).equalsIgnoreCase("water")) {
				saveWater(pid, param.subList(7, 14), value.subList(7, 14));
			}
		} else if (type.get(0).equalsIgnoreCase("water")) {
			saveWater(pid, param.subList(0, 7), value.subList(0, 7));
		}
	}

	public void saveOil(Integer pid, List<String> param, List<String> value) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> fluidModelslist = fluidRepo.findBydetails(details);
		List<ReservoirFluidModel> list = new ArrayList<ReservoirFluidModel>();
		int j = 0;
		for (int i = 0; i < fluidModelslist.size(); i++) {
			ReservoirFluidModel fluidModel = fluidModelslist.get(i);
			if (j < param.size() && fluidModel.getParam().equalsIgnoreCase(param.get(j))) {
				fluidModel.setValue(value.get(j));
				fluidModel.setFluidtype("Oil");
				list.add(fluidModel);
				j++;
			}
		}
		fluidRepo.saveAll(list);

	}

	public void saveGas(Integer pid, List<String> param, List<String> value) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> list = new ArrayList<ReservoirFluidModel>();
		ReservoirFluidModel fluidModel = null;
		for (int i = 0; i < param.size(); i++) {
			fluidModel = new ReservoirFluidModel();
			fluidModel.setDetails(details);
			fluidModel.setParam(param.get(i));
			fluidModel.setValue(value.get(i));
			fluidModel.setFluidtype("Gas");
			list.add(fluidModel);
		}
		fluidRepo.saveAll(list);
	}

	public void saveWater(Integer pid, List<String> param, List<String> value) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> list = new ArrayList<ReservoirFluidModel>();
		ReservoirFluidModel fluidModel = null;
		for (int i = 0; i < param.size(); i++) {
			fluidModel = new ReservoirFluidModel();
			fluidModel.setDetails(details);
			fluidModel.setParam(param.get(i));
			fluidModel.setValue(value.get(i));
			fluidModel.setFluidtype("water");
			list.add(fluidModel);
		}
		fluidRepo.saveAll(list);
	}

	public Map<String, ReservoirFluidModel> showSavedFluid(Integer pid, List<String> fluidtype) {
		System.out.println(fluidtype);
		Map<String, ReservoirFluidModel> map = new LinkedHashMap<>();
		if (fluidtype.contains("oil")) {
			showOil(pid, fluidtype.get(fluidtype.indexOf("oil")));
			if (fluidtype.contains("gas")) {
				showGas(pid, fluidtype.get(fluidtype.indexOf("gas")));
				if (fluidtype.contains("water")) {
					showWater(pid, fluidtype.get(fluidtype.indexOf("water")));
				}
			} else if (fluidtype.contains("water")) {
				showWater(pid, fluidtype.get(fluidtype.indexOf("water")));
			}
		} else if (fluidtype.contains("gas")) {
			showGas(pid, fluidtype.get(fluidtype.indexOf("gas")));
			if (fluidtype.contains("water")) {
				showWater(pid, fluidtype.get(fluidtype.indexOf("water")));
			}
		} else if (fluidtype.contains("water")) {
			showWater(pid, fluidtype.get(fluidtype.indexOf("water")));
		}
		return null;
	}

	public List<ReservoirFluidModel> showOil(Integer pid, String fluidtype) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		return fluidRepo.findByFluidtypeAndDetails(fluidtype, details);
	}

	public List<ReservoirFluidModel> showGas(Integer pid, String fluidtype) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		return fluidRepo.findByFluidtypeAndDetails(fluidtype, details);
	}

	public List<ReservoirFluidModel> showWater(Integer pid, String fluidtype) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		return fluidRepo.findByFluidtypeAndDetails(fluidtype, details);
	}
	
	public void updateResFluid(Integer pid, List<Integer> id, List<String> input) {
		ProjectDetails details = detailRepo.findById(pid).orElse(null);
		List<ReservoirFluidModel> list = new ArrayList<ReservoirFluidModel>();
		for(int i=0;i<id.size();i++){
			ReservoirFluidModel fluidModel=fluidRepo.getOne(id.get(i));
			fluidModel.setValue(input.get(i));
			list.add(fluidModel);
		}
		fluidRepo.saveAll(list);
	}
}
